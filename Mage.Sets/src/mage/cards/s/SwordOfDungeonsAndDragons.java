package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.DragonTokenGold;
import mage.players.Player;

import java.util.UUID;

/**
 * This creates a "gold" token which is represented as a creature with all colors
 * as the color gold is not supported in the black-bordered game rules
 *
 * @author Saga
 */
public final class SwordOfDungeonsAndDragons extends CardImpl {

    private static final FilterCard filter = new FilterCard("Rogues and from Clerics");

    static {
        filter.add(Predicates.or(
                SubType.ROGUE.getPredicate(),
                SubType.CLERIC.getPredicate()
        ));
    }

    public SwordOfDungeonsAndDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from Rogues and from Clerics.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                new ProtectionAbility(filter), AttachmentType.EQUIPMENT
        ).setText("and has protection from Rogues and from Clerics"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, you create a 4/4 gold Dragon creature token with flying and roll a d20. If you roll a 20, repeat this process.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new SwordOfDungeonsAndDragonsEffect(), "equipped creature", false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private SwordOfDungeonsAndDragons(final SwordOfDungeonsAndDragons card) {
        super(card);
    }

    @Override
    public SwordOfDungeonsAndDragons copy() {
        return new SwordOfDungeonsAndDragons(this);
    }
}

class SwordOfDungeonsAndDragonsEffect extends OneShotEffect {

    SwordOfDungeonsAndDragonsEffect() {
        super(Outcome.Benefit);
        staticText = "you create a 4/4 gold Dragon creature token with flying " +
                "and roll a d20. If you roll a 20, repeat this process";
    }

    private SwordOfDungeonsAndDragonsEffect(final SwordOfDungeonsAndDragonsEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfDungeonsAndDragonsEffect copy() {
        return new SwordOfDungeonsAndDragonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (int i = 0; i < 1000; i++) { // just in case loop goes too long
            new DragonTokenGold().putOntoBattlefield(1, game, source);
            if (controller.rollDice(outcome, source, game, 20) != 20) {
                break;
            }
        }
        return true;
    }
}
