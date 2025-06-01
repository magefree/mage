package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BusterSword extends CardImpl {

    public BusterSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 2)));

        // Whenever equipped creature deals combat damage to a player, draw a card, then you may cast a spell from your hand with mana value less than or equal to that damage without paying its mana cost.
        Ability ability = new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DrawCardSourceControllerEffect(1), "equipped", false
        );
        ability.addEffect(new BusterSwordEffect());
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private BusterSword(final BusterSword card) {
        super(card);
    }

    @Override
    public BusterSword copy() {
        return new BusterSword(this);
    }
}

class BusterSwordEffect extends OneShotEffect {

    BusterSwordEffect() {
        super(Outcome.Benefit);
        staticText = ", then you may cast a spell from your hand with mana value " +
                "less than or equal to that damage without paying its mana cost";
    }

    private BusterSwordEffect(final BusterSwordEffect effect) {
        super(effect);
    }

    @Override
    public BusterSwordEffect copy() {
        return new BusterSwordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        int damage = (Integer) getValue("damage");
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, damage + 1));
        return CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(player.getHand()), filter);
    }
}
