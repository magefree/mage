
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SubLayer;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class WoodlurkerMimic extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell that's both black and green");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    private static final String rule = "Whenever you cast a spell that's both black and green, {this} has base power and toughness 4/5 until end of turn and gains wither until end of turn.";

    public WoodlurkerMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B/G}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell that's both black and green, Woodlurker Mimic has base power and toughness 4/5 until end of turn and gains wither until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new SetBasePowerToughnessSourceEffect(4, 5, Duration.EndOfTurn, SubLayer.SetPT_7b), filter, false, rule);
        ability.addEffect(new GainAbilitySourceEffect(WitherAbility.getInstance(), Duration.EndOfTurn, false, true));
        this.addAbility(ability);

    }

    private WoodlurkerMimic(final WoodlurkerMimic card) {
        super(card);
    }

    @Override
    public WoodlurkerMimic copy() {
        return new WoodlurkerMimic(this);
    }
}
