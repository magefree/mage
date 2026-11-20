package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.OfficerToken;

/**
 * @author muz
 */
public final class CaptainJanewayEndgame extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.OFFICER, "Officers");

    public CaptainJanewayEndgame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever one or more Officers you control deal combat damage to a player, you draw a card and create a 1/1 red Officer creature token.
        Ability ability = new OneOrMoreCombatDamagePlayerTriggeredAbility(
            new DrawCardSourceControllerEffect(1, true), filter);
        ability.addEffect(new CreateTokenEffect(new OfficerToken()).concatBy("and"));
        this.addAbility(ability);

        // {2}, Tap an untapped creature you control: Captain Janeway can't be blocked this turn.
        ability = new SimpleActivatedAbility(new CantBeBlockedSourceEffect(Duration.EndOfTurn), new GenericManaCost(2));
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE));
        this.addAbility(ability);
    }

    private CaptainJanewayEndgame(final CaptainJanewayEndgame card) {
        super(card);
    }

    @Override
    public CaptainJanewayEndgame copy() {
        return new CaptainJanewayEndgame(this);
    }
}
