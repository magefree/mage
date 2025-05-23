package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedAndDiedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class UnscytheKillerOfKings extends CardImpl {

    public UnscytheKillerOfKings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}{B}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+3 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike"));
        this.addAbility(ability);

        // Whenever a creature dealt damage by equipped creature this turn dies, you may exile that card. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new DealtDamageAttachedAndDiedTriggeredAbility(new UnscytheEffect(), true,
                StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.CARD, AttachmentType.EQUIPMENT));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private UnscytheKillerOfKings(final UnscytheKillerOfKings card) {
        super(card);
    }

    @Override
    public UnscytheKillerOfKings copy() {
        return new UnscytheKillerOfKings(this);
    }
}

class UnscytheEffect extends OneShotEffect {

    UnscytheEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "exile that card. If you do, create a 2/2 black Zombie creature token";
    }

    private UnscytheEffect(final UnscytheEffect effect) {
        super(effect);
    }

    @Override
    public UnscytheEffect copy() {
        return new UnscytheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }
        if (game.getState().getZone(card.getId()) == Zone.GRAVEYARD
                && controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true)) {
            return new ZombieToken().putOntoBattlefield(1, game, source, source.getControllerId());
        }
        return false;
    }
}
