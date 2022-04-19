package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GiveManaAbilityAndCastSourceAbility;
import mage.abilities.effects.common.combat.CantAttackBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SparasAdjudicators extends CardImpl {

    public SparasAdjudicators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Spara's Adjudicators enters the battlefield, target creature an opponent controls can't attack or block until your next turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new CantAttackBlockTargetEffect(Duration.UntilYourNextTurn)
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // {2}, Exile Spara's Adjudicators from your hand: Target land gains "{T}: Add {G}, {W}, or {U}" until Spara's Adjudicators is cast from exile. You may cast Spara's Adjudicators for as long as it remains exiled.
        this.addAbility(new GiveManaAbilityAndCastSourceAbility("GWU"));
    }

    private SparasAdjudicators(final SparasAdjudicators card) {
        super(card);
    }

    @Override
    public SparasAdjudicators copy() {
        return new SparasAdjudicators(this);
    }
}
