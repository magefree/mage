package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ApexAltisaur extends CardImpl {

    public ApexAltisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        Effect effect = new FightTargetSourceEffect();
        effect.setText("it fights up to one target creature you don't control");

        // When Apex Altisaur enters the battlefield, it fights up to one target creature you don't control.
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
        this.addAbility(ability);

        // Enrage — Whenever Apex Altisaur is dealt damage, it fights up to one target creature you don't control.
        ability = new DealtDamageToSourceTriggeredAbility(effect, false, true);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
        this.addAbility(ability);
    }

    private ApexAltisaur(final ApexAltisaur card) {
        super(card);
    }

    @Override
    public ApexAltisaur copy() {
        return new ApexAltisaur(this);
    }
}
