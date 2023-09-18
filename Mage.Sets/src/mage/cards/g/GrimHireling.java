package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimHireling extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.TREASURE, "Treasures");

    public GrimHireling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever one or more creatures you control deal combat damage to a player, create two Treasure tokens.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 2)
        ));

        // {B}, Sacrifice X Treasures: Target creature gets -X/-X until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new GrimHirelingEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeXTargetCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GrimHireling(final GrimHireling card) {
        super(card);
    }

    @Override
    public GrimHireling copy() {
        return new GrimHireling(this);
    }
}

class GrimHirelingEffect extends OneShotEffect {

    GrimHirelingEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gets -X/-X until end of turn";
    }

    private GrimHirelingEffect(final GrimHirelingEffect effect) {
        super(effect);
    }

    @Override
    public GrimHirelingEffect copy() {
        return new GrimHirelingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = GetXValue.instance.calculate(game, source, this);
        game.addEffect(new BoostTargetEffect(-xValue, -xValue), source);
        return true;
    }
}
