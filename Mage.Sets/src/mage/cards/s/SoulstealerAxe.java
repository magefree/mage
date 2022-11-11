package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SoulstealerAxe extends CardImpl {

    public SoulstealerAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Whenever equipped creature deals combat damage to a player, seek a card with mana value equal to that damage.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new SoulstealerAxeEffect(), "equipped", false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private SoulstealerAxe(final SoulstealerAxe card) {
        super(card);
    }

    @Override
    public SoulstealerAxe copy() {
        return new SoulstealerAxe(this);
    }
}

class SoulstealerAxeEffect extends OneShotEffect {

    SoulstealerAxeEffect() {
        super(Outcome.Benefit);
        staticText = "seek a card with mana value equal to that damage";
    }

    private SoulstealerAxeEffect(final SoulstealerAxeEffect effect) {
        super(effect);
    }

    @Override
    public SoulstealerAxeEffect copy() {
        return new SoulstealerAxeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int damage = (Integer) getValue("damage");
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, damage));
        return player.seekCard(filter, source, game);
    }
}
