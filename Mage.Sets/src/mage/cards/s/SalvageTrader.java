package mage.cards.s;

import java.util.*;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author NinthWorld
 */
public final class SalvageTrader extends CardImpl {

    private static final FilterArtifactPermanent filterYou = new FilterArtifactPermanent("artifact you control");

    static {
        filterYou.add(new ControllerPredicate(TargetController.YOU));
    }

    public SalvageTrader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        
        this.subtype.add(SubType.CROLUTE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Exchange control of target artifact you control and target artifact an opponent controls with the same converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SalvageTraderEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filterYou));
        this.addAbility(ability);
    }

    public SalvageTrader(final SalvageTrader card) {
        super(card);
    }

    @Override
    public SalvageTrader copy() {
        return new SalvageTrader(this);
    }
}

// effect is based on JuxtaposeEffect
// which is based on ExchangeControlTargetEffect
class SalvageTraderEffect extends ContinuousEffectImpl {

    private final Map<UUID, Integer> zoneChangeCounter;
    private final Map<UUID, UUID> lockedControllers;

    public SalvageTraderEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "Exchange control of target artifact you control and target artifact an opponent controls with the same converted mana cost";

        this.zoneChangeCounter = new HashMap<>();
        this.lockedControllers = new HashMap<>();
    }

    public SalvageTraderEffect(final SalvageTraderEffect effect) {
        super(effect);

        this.zoneChangeCounter = new HashMap<>(effect.zoneChangeCounter);
        this.lockedControllers = new HashMap<>(effect.lockedControllers);
    }

    @Override
    public void init(Ability source, Game game) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent permanent1 = game.getPermanent(targetPointer.getFirst(game, source));
        MageObject sourceObject = game.getCard(source.getSourceId());

        if(you != null && permanent1 != null) {
            FilterArtifactCard filterArtifactCard = new FilterArtifactCard();
            filterArtifactCard.add(new ControllerPredicate(TargetController.OPPONENT));
            filterArtifactCard.add(new SpellZonePredicate(Zone.BATTLEFIELD));
            filterArtifactCard.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, permanent1.getConvertedManaCost()));

            FilterArtifactPermanent filterArtifactPermanent = new FilterArtifactPermanent();
            filterArtifactPermanent.add(new ControllerPredicate(TargetController.OPPONENT));
            filterArtifactPermanent.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, permanent1.getConvertedManaCost()));

            Cards cards = new CardsImpl();
            for(Permanent permanent : game.getBattlefield().getAllActivePermanents(filterArtifactPermanent, game)) {
                cards.add(permanent);
            }

            Player opponent = null;
            Permanent permanent2 = null;
            TargetCard targetCard = new TargetCard(Zone.BATTLEFIELD, filterArtifactCard);
            if(you.choose(Outcome.Benefit, cards, targetCard, game)) {
                permanent2 = game.getPermanent(targetCard.getFirstTarget());
                if(permanent2 != null) {
                    opponent = game.getPlayer(permanent2.getControllerId());
                }
            }

            if (opponent != null) {
                // exchange works only for two different controllers
                if (permanent1.isControlledBy(permanent2.getControllerId())) {
                    // discard effect if controller of both permanents is the same
                    discard();
                    return;
                }
                this.lockedControllers.put(permanent1.getId(), permanent2.getControllerId());
                this.zoneChangeCounter.put(permanent1.getId(), permanent1.getZoneChangeCounter(game));
                this.lockedControllers.put(permanent2.getId(), permanent1.getControllerId());
                this.zoneChangeCounter.put(permanent2.getId(), permanent2.getZoneChangeCounter(game));

                permanent1.changeControllerId(opponent.getId(), game);
                permanent2.changeControllerId(you.getId(), game);
                game.informPlayers(new StringBuilder(sourceObject != null ? sourceObject.getLogName() : "").append(": ").append(you.getLogName())
                        .append(" and ").append(opponent.getLogName()).append(" exchange control of ").append(permanent1.getLogName())
                        .append(" and ").append(permanent2.getName()).toString());
            } else {
                // discard if there are less than 2 permanents
                discard();
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toDelete = new HashSet<>();
        for (Map.Entry<UUID, Integer> entry : zoneChangeCounter.entrySet()) {
            Permanent permanent = game.getPermanent(entry.getKey());
            if (permanent == null || permanent.getZoneChangeCounter(game) != entry.getValue()) {
                // control effect cease if the same permanent is no longer on the battlefield
                toDelete.add(entry.getKey());
                continue;
            }
            permanent.changeControllerId(lockedControllers.get(permanent.getId()), game);
        }
        if (!toDelete.isEmpty()) {
            for (UUID uuid : toDelete) {
                zoneChangeCounter.remove(uuid);
            }
            if (zoneChangeCounter.isEmpty()) {
                discard();
                return false;
            }
        }
        return true;
    }

    @Override
    public SalvageTraderEffect copy() {
        return new SalvageTraderEffect(this);
    }
}