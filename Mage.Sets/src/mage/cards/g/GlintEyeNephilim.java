package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 * @author fenhl
 */
public final class GlintEyeNephilim extends CardImpl {

    public GlintEyeNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}{R}{G}");
        this.subtype.add(SubType.NEPHILIM);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Glint-Eye Nephilim deals combat damage to a player, draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY),
                false, true));

        // {1}, Discard a card: Glint-Eye Nephilim gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), new GenericManaCost(1));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);

    }

    private GlintEyeNephilim(final GlintEyeNephilim card) {
        super(card);
    }

    @Override
    public GlintEyeNephilim copy() {
        return new GlintEyeNephilim(this);
    }
}
