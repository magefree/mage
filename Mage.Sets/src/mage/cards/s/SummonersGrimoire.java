package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SummonersGrimoire extends CardImpl {

    public SummonersGrimoire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature is a Shaman in addition to its other types and has "Whenever this creature attacks, you may put a creature card from your hand onto the battlefield. If that card is an enchantment card, it enters tapped and attacking."
        Ability ability = new SimpleStaticAbility(new AddCardSubtypeAttachedEffect(
                SubType.SHAMAN, AttachmentType.EQUIPMENT
        ).setText("equipped creature is a Shaman in addition to its other types"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new SummonersGrimoireEffect()), AttachmentType.EQUIPMENT
        ).setText("and has \"Whenever this creature attacks, you may put a creature card from your hand " +
                "onto the battlefield. If that card is an enchantment card, it enters tapped and attacking.\""));
        this.addAbility(ability);

        // Abraxas -- Equip {3}
        this.addAbility(new EquipAbility(3).withFlavorWord("Abraxas"));
    }

    private SummonersGrimoire(final SummonersGrimoire card) {
        super(card);
    }

    @Override
    public SummonersGrimoire copy() {
        return new SummonersGrimoire(this);
    }
}

class SummonersGrimoireEffect extends OneShotEffect {

    SummonersGrimoireEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card from your hand onto the battlefield. " +
                "If that card is an enchantment card, it enters tapped and attacking";
    }

    private SummonersGrimoireEffect(final SummonersGrimoireEffect effect) {
        super(effect);
    }

    @Override
    public SummonersGrimoireEffect copy() {
        return new SummonersGrimoireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, player.getHand(), target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        boolean flag = card.isEnchantment(game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game, flag, false, false, null);
        if (flag) {
            game.getCombat().addAttackingCreature(card.getId(), game);
        }
        return true;
    }
}
