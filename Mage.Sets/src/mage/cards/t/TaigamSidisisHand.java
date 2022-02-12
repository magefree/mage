package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class TaigamSidisisHand extends CardImpl {

    public TaigamSidisisHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SkipDrawStepEffect()));

        // At the beginning of your upkeep, look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LookLibraryAndPickControllerEffect(StaticValue.get(3), false, StaticValue.get(1),
                StaticFilters.FILTER_CARD, Zone.GRAVEYARD, false, false, false, Zone.HAND, false), TargetController.YOU, false));

        // {B}, {T}, Exile X cards from your graveyard: Target creature gets -X/-X until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TaigamSidisisHandEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TaigamSidisisHand(final TaigamSidisisHand card) {
        super(card);
    }

    @Override
    public TaigamSidisisHand copy() {
        return new TaigamSidisisHand(this);
    }
}

class TaigamSidisisHandEffect extends OneShotEffect {

    public TaigamSidisisHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "creature gets -X/-X until end of turn";
    }

    public TaigamSidisisHandEffect(final TaigamSidisisHandEffect effect) {
        super(effect);
    }

    @Override
    public TaigamSidisisHandEffect copy() {
        return new TaigamSidisisHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));

            if (targetCreature != null) {
                int amount = 0;
                for (Cost cost : source.getCosts()) {
                    if (cost instanceof ExileFromGraveCost) {
                        amount = ((ExileFromGraveCost) cost).getExiledCards().size();
                        ContinuousEffect effect = new BoostTargetEffect(-amount, -amount, Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(source.getTargets().getFirstTarget(), game));
                        game.addEffect(effect, source);
                    }
                }
            }
        }
        return false;
    }
}
