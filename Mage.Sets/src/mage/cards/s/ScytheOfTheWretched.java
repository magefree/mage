package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedAndDiedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Jason E. Wall
 *
 */
public final class ScytheOfTheWretched extends CardImpl {

    public ScytheOfTheWretched(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 2, Duration.WhileOnBattlefield)));

        // Whenever a creature dealt damage by equipped creature this turn dies, return that card to the battlefield under your control. Attach Scythe of the Wretched to that creature.
        this.addAbility(new DealtDamageAttachedAndDiedTriggeredAbility(new ScytheOfTheWretchedReanimateEffect(), false,
                StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.CARD, AttachmentType.EQUIPMENT));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4), false));
    }

    private ScytheOfTheWretched(final ScytheOfTheWretched card) {
        super(card);
    }

    @Override
    public ScytheOfTheWretched copy() {
        return new ScytheOfTheWretched(this);
    }
}

class ScytheOfTheWretchedReanimateEffect extends OneShotEffect {

    ScytheOfTheWretchedReanimateEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return that card to the battlefield under your control. Attach {this} to that creature";
    }

    private ScytheOfTheWretchedReanimateEffect(final ScytheOfTheWretchedReanimateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (card == null || controller == null) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent != null) {
            Effect effect = new AttachEffect(Outcome.AddAbility);
            effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
            effect.apply(game, source);
        }
        return true;
    }

    @Override
    public ScytheOfTheWretchedReanimateEffect copy() {
        return new ScytheOfTheWretchedReanimateEffect(this);
    }
}
