
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public final class WoodElemental extends CardImpl {

    public WoodElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Wood Elemental enters the battlefield, sacrifice any number of untapped Forests.
        this.addAbility(new AsEntersBattlefieldAbility(new WoodElementalEffect()));

        // Wood Elemental's power and toughness are each equal to the number of Forests sacrificed as it entered the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this}'s power and toughness are each equal to the number of Forests sacrificed as it entered the battlefield")));
    }

    private WoodElemental(final WoodElemental card) {
        super(card);
    }

    @Override
    public WoodElemental copy() {
        return new WoodElemental(this);
    }
}

class WoodElementalEffect extends OneShotEffect {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Forests you control");
    
    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.FOREST.getPredicate());
    }

    public WoodElementalEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice any number of untapped Forests";
    }

    public WoodElementalEffect(final WoodElementalEffect effect) {
        super(effect);
    }

    @Override
    public WoodElementalEffect copy() {
        return new WoodElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && sourceCard != null) {
            Target target = new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true);
            if (target.canChoose(source.getControllerId(), source, game)
                    && controller.chooseTarget(Outcome.Detriment, target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    int sacrificedForests = target.getTargets().size();
                    game.informPlayers(controller.getLogName() + " sacrifices " + sacrificedForests + " untapped Forests for " + sourceCard.getLogName());
                    for (UUID targetId : target.getTargets()) {
                        Permanent targetPermanent = game.getPermanent(targetId);
                        if (targetPermanent != null) {
                            targetPermanent.sacrifice(source, game);
                        }
                    }
                    game.addEffect(new SetBasePowerToughnessSourceEffect(sacrificedForests, sacrificedForests, Duration.Custom, SubLayer.SetPT_7b), source);
                    return true;
                }
            }
        }
        return false;
    }
}
