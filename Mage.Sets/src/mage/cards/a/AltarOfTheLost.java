package mage.cards.a;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author BetaSteward
 */
public final class AltarOfTheLost extends CardImpl {

    public AltarOfTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Altar of the Lost enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add two mana in any combination of colors. Spend this mana only to cast spells with flashback from a graveyard.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new AltarOfTheLostManaBuilder()));
    }

    private AltarOfTheLost(final AltarOfTheLost card) {
        super(card);
    }

    @Override
    public AltarOfTheLost copy() {
        return new AltarOfTheLost(this);
    }
}

class AltarOfTheLostManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new AltarOfTheLostConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast spells with flashback from a graveyard";
    }
}

class AltarOfTheLostConditionalMana extends ConditionalMana {

    public AltarOfTheLostConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast spells with flashback from a graveyard";
        addCondition(new AltarOfTheLostManaCondition());
    }
}

class AltarOfTheLostManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (game.inCheckPlayableState()) {
            if (object instanceof Card && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                for (Ability ability : ((Card) object).getAbilities(game)) {
                    if (ability instanceof FlashbackAbility) {
                        return true;
                    }
                }
            }

        }
        if (object instanceof Spell && ((Spell) object).getFromZone() == Zone.GRAVEYARD) {
            for (Ability ability : ((Spell) object).getAbilities(game)) {
                if (ability instanceof FlashbackAbility) {
                    return true;
                }
            }
        }
        return false;
    }
}
