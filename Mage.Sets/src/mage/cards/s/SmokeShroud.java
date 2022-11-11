package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmokeShroud extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.NINJA, "");

    public SmokeShroud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has flying.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).setText("and has flying"));
        this.addAbility(ability);

        // When a Ninja enters the battlefield under your control, you may return Smoke Shroud from your graveyard to the battlefield attached to that creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.GRAVEYARD, new SmokeShroudEffect(), filter, true,
                SetTargetPointer.PERMANENT, "When a Ninja enters the battlefield under your control, " +
                "you may return {this} from your graveyard to the battlefield attached to that creature."
        ));
    }

    private SmokeShroud(final SmokeShroud card) {
        super(card);
    }

    @Override
    public SmokeShroud copy() {
        return new SmokeShroud(this);
    }
}

class SmokeShroudEffect extends OneShotEffect {

    SmokeShroudEffect() {
        super(Outcome.Benefit);
    }

    private SmokeShroudEffect(final SmokeShroudEffect effect) {
        super(effect);
    }

    @Override
    public SmokeShroudEffect copy() {
        return new SmokeShroudEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card sourceCard = (Card) source.getSourceObjectIfItStillExists(game);
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceCard != null && permanent != null && controller != null) {
            game.getState().setValue("attachTo:" + sourceCard.getId(), permanent);
            if (controller.moveCards(sourceCard, Zone.BATTLEFIELD, source, game)) {
                permanent.addAttachment(sourceCard.getId(), source, game);
            }
            return true;
        }
        return false;
    }
}

