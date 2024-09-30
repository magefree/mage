package mage.cards.s;

import mage.MageInt;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkullsnapNuisance extends CardImpl {

    public SkullsnapNuisance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, surveil 1.
        this.addAbility(new EerieAbility(new SurveilEffect(1)));
    }

    private SkullsnapNuisance(final SkullsnapNuisance card) {
        super(card);
    }

    @Override
    public SkullsnapNuisance copy() {
        return new SkullsnapNuisance(this);
    }
}
