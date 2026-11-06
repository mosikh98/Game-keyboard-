# HeliBoard Persian (فورک فارسی هلی‌بورد)

> **English** · [فارسی](#فارسی)

**This is a fork of [HeliBoard](https://github.com/HeliBorg/HeliBoard).**
The original project, and the root of all the code here, is **https://github.com/HeliBorg/HeliBoard**
(which itself is based on AOSP / OpenBoard). All credit for the keyboard goes to the HeliBoard authors and contributors.
The original README is kept as [README.upstream.md](README.upstream.md).

## What is different in this fork

### Visual Persian toggle (for games and apps without RTL support)
Many games draw every string left-to-right and do no letter shaping, so normally typed Persian shows up
disconnected and reversed. This fork adds a toggle button that types Persian the way
[timecode.ir/font-converter](https://www.timecode.ir/font-converter/) converts it:

| typed | committed to the text field |
|---|---|
| `سلام چطورید` | `ﺪﯾﺭﻮﻄﭼ ﻡﺎﻠﺳ` |

- Button: the **swap-arrows** icon, pinned in the corner of the suggestion strip (always visible). Tap to switch on / off. It is bright when on, dimmed when off.
- When on, every key press re-renders the whole typed run (contextual letter forms + reversed order), so the previous letter changes shape correctly while you type, and backspace works letter by letter.
- Digits and Latin words keep their order, brackets are mirrored, ZWNJ (نیم‌فاصله) breaks joining and is dropped, diacritics stay on their letter.
- Lam-alef ligatures are not used (lam and alef stay separate glyphs, like the converter site).
- Text typed before the first Persian letter, or after you moved the cursor, is never touched. Enter, cursor moves and most other keys end the current run.
- Suggestions / auto-correction are off for the converted text. The game's font must contain the Arabic Presentation Forms glyphs (same requirement as pasting text from the converter site).
- The button can also be moved or hidden in *Settings → Toolbar*.

### New look
- New default **Firouzeh** (فیروزه‌ای / turquoise) keyboard themes, light and dark, with the **Rounded** key style and key borders on by default (all other themes are still available).
- Redesigned app settings: turquoise Material 3 palette, rounder shapes.
- New launcher icon colors, app name "HeliBoard Persian".

## Building
Push to GitHub and the **Build APK** workflow (`.github/workflows/build-apk.yml`) builds the APK:
- every push to `main` / pull request / manual run (*Actions → Build APK → Run workflow*): download the APK from the run's **Artifacts**
- push a tag like `v1.0.0`: the APK is attached to a GitHub release

Locally: `./gradlew assembleDebug` (JDK 17, Android SDK + NDK 28.0.13004108).
The debug build uses the application id `helium314.keyboard.debug`, so it installs next to the original HeliBoard.

## Status
Experimental. The conversion logic has a unit test (`PersianVisualTest`), but how it behaves in a given game depends on that game's text field and font.

## License and credits
GPL-3.0, same as upstream, see [LICENSE](LICENSE). Original work by the HeliBoard / OpenBoard / AOSP authors.
This fork's changes were developed with AI assistance (Claude) and are disclosed here as the upstream
[AI usage policy](AI_USAGE.md) asks. Because of that policy, **please do not open pull requests or issues about this fork on the upstream repository**;
send bugs about the original keyboard upstream, and use this fork's own issues for the changes described above.

---

## فارسی

**این فورکی از [HeliBoard](https://github.com/HeliBorg/HeliBoard) است.**
پروژهٔ اصلی و ریشهٔ تمام کدها این‌جاست: **https://github.com/HeliBorg/HeliBoard** (که خودش بر پایهٔ AOSP / OpenBoard ساخته شده).
تمام اعتبار کیبورد مال سازندگان و مشارکت‌کنندگان هلی‌بورد است. README اصلی در [README.upstream.md](README.upstream.md) نگه داشته شده.

### چه چیزی اضافه شده

**دکمهٔ «فارسی بصری» (برای بازی‌ها و برنامه‌هایی که راست‌به‌چپ پشتیبانی نمی‌کنند)**
خیلی از بازی‌ها متن را چپ‌به‌راست و بدون چسباندن حروف رسم می‌کنند، برای همین فارسی معمولی جدا و برعکس نمایش داده می‌شود.
این فورک یک دکمه دارد که فارسی را دقیقاً مثل [مبدل timecode.ir](https://www.timecode.ir/font-converter/) تایپ می‌کند:
`سلام چطورید` ← `ﺪﯾﺭﻮﻄﭼ ﻡﺎﻠﺳ`

- دکمه با آیکن **فلش‌های جابه‌جایی** گوشهٔ نوار پیشنهادها (همیشه دیده می‌شود) قرار دارد؛ با یک لمس روشن/خاموش می‌شود. روشن که باشد پررنگ است.
- در حالت روشن، با هر کلید کل متن تایپ‌شده دوباره ساخته می‌شود (شکل درست حروف + ترتیب برعکس)، پس شکل حرف قبلی هنگام تایپ درست عوض می‌شود و بک‌اسپیس هم حرف‌به‌حرف کار می‌کند.
- اعداد و کلمه‌های لاتین ترتیبشان را حفظ می‌کنند، پرانتزها قرینه می‌شوند، نیم‌فاصله اتصال را قطع می‌کند و در خروجی نمی‌آید.
- لام‌الف به‌صورت لیگاتور درنمی‌آید (مثل سایت مبدل، لام و الف جدا می‌مانند).
- متنی که قبل از اولین حرف فارسی نوشته‌اید یا بعد از جابه‌جا کردن مکان‌نما، دست‌نخورده می‌ماند. اینتر و جابه‌جایی مکان‌نما تایپ جاری را تمام می‌کنند.
- در این حالت پیشنهاد کلمه و تصحیح خودکار خاموش است. فونت بازی باید حروف Arabic Presentation Forms را داشته باشد (همان شرطی که برای چسباندن متن از سایت مبدل لازم است).

**ظاهر جدید**
- تم پیش‌فرض جدید **فیروزه‌ای** (روشن و تیره) با کلیدهای گرد و لبه‌دار؛ بقیهٔ تم‌ها هنوز هستند.
- ظاهر تنظیمات برنامه با پالت فیروزه‌ای و گوشه‌های گردتر بازطراحی شده.
- رنگ آیکن برنامه و نام برنامه («HeliBoard Persian») عوض شده.

### ساخت (بیلد)
پروژه را روی گیتهاب بگذارید؛ ورک‌فلو **Build APK** فایل APK را می‌سازد:
- با هر push روی `main`، pull request یا اجرای دستی (*Actions ← Build APK ← Run workflow*): APK را از بخش **Artifacts** همان اجرا دانلود کنید.
- با push یک تگ مثل `v1.0.0`: APK به یک Release گیتهاب پیوست می‌شود.

نسخهٔ debug با شناسهٔ `helium314.keyboard.debug` ساخته می‌شود و کنار هلی‌بورد اصلی نصب می‌شود.

### مجوز
GPL-3.0 مثل پروژهٔ اصلی. تغییرات این فورک با کمک هوش مصنوعی (Claude) نوشته شده و طبق [سیاست استفاده از AI](AI_USAGE.md) پروژهٔ اصلی این‌جا اعلام می‌شود؛ به همین دلیل **لطفاً دربارهٔ این فورک در ریپوی اصلی issue یا pull request نفرستید**.
