package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Blinke
 */
public final class Deathrender extends CardImpl {

    public Deathrender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        // Whenever equipped creature dies, you may put a creature card from your hand onto the battlefield and attach Deathrender to it.
        this.addAbility(new DiesAttachedTriggeredAbility(new DeathrenderEffect(), "equipped creature"));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private Deathrender(final Deathrender card) {
        super(card);
    }

    @Override
    public Deathrender copy() {
        return new Deathrender(this);
    }
}

class DeathrenderEffect extends OneShotEffect {

    DeathrenderEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may put a creature card from your hand onto the battlefield and attach {this} to it";
    }

    DeathrenderEffect(final DeathrenderEffect effect) {
        super(effect);
    }

    @Override
    public DeathrenderEffect copy() {
        return new DeathrenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            TargetCardInHand target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_CREATURE);
            if (controller.choose(Outcome.PutCardInPlay, target, source.getSourceId(), game)) {
                Card creatureInHand = game.getCard(target.getFirstTarget());
                if (creatureInHand != null) {
                    if (controller.moveCards(creatureInHand, Zone.BATTLEFIELD, source, game)) {
                        game.getPermanent(creatureInHand.getId()).addAttachment(sourcePermanent.getId(), source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
