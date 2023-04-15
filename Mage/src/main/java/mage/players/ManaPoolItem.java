package mage.players;

import java.io.Serializable;
import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.Emptiable;
import mage.constants.Duration;
import mage.constants.ManaType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ManaPoolItem implements Serializable, Emptiable {

    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private int white = 0;
    private int black = 0;
    private int colorless = 0;
    private ConditionalMana conditionalMana;
    private MageObject sourceObject; // source of the mana, can be null (what's use case for null values? JayDi85)
    private UUID originalId; // originalId of the mana producing ability
    private boolean flag = false;
    private Duration duration;
    private int stock; // amount the item had at the start of casting something

    public ManaPoolItem() {
    }

    public ManaPoolItem(int red, int green, int blue, int white, int black, int colorless, MageObject sourceObject, UUID originalId, boolean flag) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.white = white;
        this.black = black;
        this.colorless = colorless;
        this.sourceObject = sourceObject;
        this.originalId = originalId;
        this.flag = flag;
        this.duration = Duration.EndOfStep;
    }

    public ManaPoolItem(ConditionalMana conditionalMana, MageObject sourceObject, UUID originalId) {
        this.conditionalMana = conditionalMana;
        this.sourceObject = sourceObject;
        this.originalId = originalId;
        this.conditionalMana.setManaProducerId(sourceObject.getId());
        this.conditionalMana.setManaProducerOriginalId(originalId);
        this.flag = conditionalMana.getFlag();
        this.duration = Duration.EndOfStep;
    }

    public ManaPoolItem(final ManaPoolItem item) {
        this.red = item.red;
        this.green = item.green;
        this.blue = item.blue;
        this.white = item.white;
        this.black = item.black;
        this.colorless = item.colorless;
        if (item.conditionalMana != null) {
            this.conditionalMana = item.conditionalMana.copy();
        }
        this.sourceObject = item.sourceObject;
        this.originalId = item.originalId;
        this.flag = item.flag;
        this.duration = item.duration;
        this.stock = item.stock;
    }

    public ManaPoolItem copy() {
        return new ManaPoolItem(this);
    }

    public MageObject getSourceObject() {
        return sourceObject;
    }

    public UUID getSourceId() {
        if (sourceObject != null) {
            return sourceObject.getId();
        }
        return null;
    }

    public UUID getOriginalId() {
        return originalId;
    }

    public boolean getFlag() {
        return flag;
    }

    public int getRed() {
        return red;
    }

    public void removeRed() {
        if (red > 0) {
            red--;
        }
    }

    public int getGreen() {
        return green;
    }

    public void removeGreen() {
        if (green > 0) {
            green--;
        }
    }

    public int getBlue() {
        return blue;
    }

    public void removeBlue() {
        if (blue > 0) {
            blue--;
        }
    }

    public int getBlack() {
        return black;
    }

    public void removeBlack() {
        if (black > 0) {
            black--;
        }
    }

    public int getWhite() {
        return white;
    }

    public void removeWhite() {
        if (white > 0) {
            white--;
        }
    }

    public int getColorless() {
        return colorless;
    }

    public void removeColorless() {
        if (colorless > 0) {
            colorless--;
        }
    }

    public boolean isConditional() {
        return conditionalMana != null;
    }

    public ConditionalMana getConditionalMana() {
        return conditionalMana;
    }

    public Mana getMana() {
        return new Mana(white, blue, black, red, green, 0, 0, colorless);
    }

    public int count() {
        if (conditionalMana == null) {
            return red + green + blue + white + black + colorless;
        }
        return conditionalMana.count();
    }

    public int get(ManaType manaType) {
        switch (manaType) {
            case BLACK:
                return black;
            case BLUE:
                return blue;
            case GREEN:
                return green;
            case RED:
                return red;
            case WHITE:
                return white;
            case COLORLESS:
                return colorless;
        }
        return 0;
    }

    public ManaType getFirstAvailable() {
        if (black > 0) {
            return ManaType.BLACK;
        } else if (blue > 0) {
            return ManaType.BLUE;
        } else if (green > 0) {
            return ManaType.GREEN;
        } else if (red > 0) {
            return ManaType.RED;
        } else if (white > 0) {
            return ManaType.WHITE;
        } else if (colorless > 0) {
            return ManaType.COLORLESS;
        }
        if (conditionalMana != null) {
            if (conditionalMana.getBlack() > 0) {
                return ManaType.BLACK;
            } else if (conditionalMana.getBlue() > 0) {
                return ManaType.BLUE;
            } else if (conditionalMana.getGreen() > 0) {
                return ManaType.GREEN;
            } else if (conditionalMana.getRed() > 0) {
                return ManaType.RED;
            } else if (conditionalMana.getWhite() > 0) {
                return ManaType.WHITE;
            } else if (conditionalMana.getColorless() > 0) {
                return ManaType.COLORLESS;
            }
        }
        return null;
    }

    public void remove(ManaType manaType) {
        int oldCount = count();
        switch (manaType) {
            case BLACK:
                if (black > 0) {
                    black--;
                }
                break;
            case BLUE:
                if (blue > 0) {
                    blue--;
                }
                break;
            case GREEN:
                if (green > 0) {
                    green--;
                }
                break;
            case RED:
                if (red > 0) {
                    red--;
                }
                break;
            case WHITE:
                if (white > 0) {
                    white--;
                }
                break;
            case COLORLESS:
                if (colorless > 0) {
                    colorless--;
                }
                break;
        }
        if (stock == oldCount && oldCount > count()) {
            stock--;
        }
    }

    public void clear(ManaType manaType) {
        switch (manaType) {
            case BLACK:
                black = 0;
                break;
            case BLUE:
                blue = 0;
                break;
            case GREEN:
                green = 0;
                break;
            case RED:
                red = 0;
                break;
            case WHITE:
                white = 0;
                break;
            case COLORLESS:
                colorless = 0;
                break;
        }
    }

    public void add(ManaType manaType, int amount) {
        switch (manaType) {
            case BLACK:
                black += amount;
                break;
            case BLUE:
                blue += amount;
                break;
            case GREEN:
                green += amount;
                break;
            case RED:
                red += amount;
                break;
            case WHITE:
                white += amount;
                break;
            case COLORLESS:
                colorless += amount;
                break;
        }
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
