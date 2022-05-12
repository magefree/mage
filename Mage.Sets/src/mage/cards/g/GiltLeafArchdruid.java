package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class GiltLeafArchdruid extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Druid spell");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.DRUID, "untapped Druids you control");

    static {
        filter.add(SubType.DRUID.getPredicate());
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public GiltLeafArchdruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Druid spell, you may draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, true
        ));

        // Tap seven untapped Druids you control: Gain control of all lands target player controls.
        Ability ability = new SimpleActivatedAbility(
                new GiltLeafArchdruidEffect(),
                new TapTargetCost(new TargetControlledPermanent(7, filter2))
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GiltLeafArchdruid(final GiltLeafArchdruid card) {
        super(card);
    }

    @Override
    public GiltLeafArchdruid copy() {
        return new GiltLeafArchdruid(this);
    }
}

class GiltLeafArchdruidEffect extends OneShotEffect {

    public GiltLeafArchdruidEffect() {
        super(Outcome.GainControl);
        this.staticText = "gain control of all lands target player controls";
    }

    public GiltLeafArchdruidEffect(final GiltLeafArchdruidEffect effect) {
        super(effect);
    }

    @Override
    public GiltLeafArchdruidEffect copy() {
        return new GiltLeafArchdruidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LANDS, targetPlayer.getId(), game)) {
                if (permanent != null) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
