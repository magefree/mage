package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class TomBertAndWilliam extends CardImpl {

    public TomBertAndWilliam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // {1}, Sacrifice another creature: Draw cards equal to the sacrificed creature's power, then discard a card.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(SacrificeCostCreaturesPower.instance)
                .setText("draw cards equal to the sacrificed creature's power"),
            new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addEffect(new DiscardControllerEffect(1).concatBy(", then"));
        this.addAbility(ability);

        // When Tom, Bert, and William die, if they were a creature, return them to the battlefield. They're an artifact. (They're no longer a creature.)
        this.addAbility(new TomBertAndWilliamDiesAbility());
    }

    private TomBertAndWilliam(final TomBertAndWilliam card) {
        super(card);
    }

    @Override
    public TomBertAndWilliam copy() {
        return new TomBertAndWilliam(this);
    }
}

class TomBertAndWilliamDiesAbility extends DiesSourceTriggeredAbility {

    TomBertAndWilliamDiesAbility() {
        super(new TomBertAndWilliamReturnEffect(), false);
        setTriggerPhrase("When {this} dies, if they were a creature, ");
    }

    private TomBertAndWilliamDiesAbility(final TomBertAndWilliamDiesAbility ability) {
        super(ability);
    }

    @Override
    public TomBertAndWilliamDiesAbility copy() {
        return new TomBertAndWilliamDiesAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || !event.getTargetId().equals(getSourceId())) {
            return false;
        }
        Permanent lki = zEvent.getTarget();
        if (lki == null || !lki.isCreature(game)) {
            return false;
        }
        getEffects().setValue("permanentLeftBattlefield", lki);
        return true;
    }
}

class TomBertAndWilliamReturnEffect extends OneShotEffect {

    TomBertAndWilliamReturnEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "return them to the battlefield. They're an artifact";
    }

    private TomBertAndWilliamReturnEffect(final TomBertAndWilliamReturnEffect effect) {
        super(effect);
    }

    @Override
    public TomBertAndWilliamReturnEffect copy() {
        return new TomBertAndWilliamReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller == null || card == null
                || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent != null) {
            game.addEffect(new TomBertAndWilliamBecomeArtifactEffect(permanent, game), source);
        }
        return true;
    }
}

class TomBertAndWilliamBecomeArtifactEffect extends ContinuousEffectImpl {

    TomBertAndWilliamBecomeArtifactEffect(Permanent permanent, Game game) {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        this.setTargetPointer(new FixedTarget(permanent, game));
        this.dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    private TomBertAndWilliamBecomeArtifactEffect(final TomBertAndWilliamBecomeArtifactEffect effect) {
        super(effect);
    }

    @Override
    public TomBertAndWilliamBecomeArtifactEffect copy() {
        return new TomBertAndWilliamBecomeArtifactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        permanent.addCardType(game, CardType.ARTIFACT);
        permanent.removeCardType(game, CardType.CREATURE);
        permanent.removeAllCreatureTypes(game);
        return true;
    }
}
