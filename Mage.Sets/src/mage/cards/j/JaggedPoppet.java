
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jerekwilson
 */
public final class JaggedPoppet extends CardImpl {

    public JaggedPoppet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Jagged Poppet is dealt damage, discard that many cards.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new JaggedPoppetDealtDamageEffect(), false, false));

        // Hellbent - Whenever Jagged Poppet deals combat damage to a player, if you have no cards in hand, that player discards cards equal to the damage.
        Ability hellbentAbility = new ConditionalInterveningIfTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new JaggedPoppetDealsDamageEffect(), false, true),
                HellbentCondition.instance,
                "<i>Hellbent</i> &mdash; Whenever {this} deals combat damage to a player, if you have no cards in hand, that player discards cards equal to the damage.");
        hellbentAbility.setAbilityWord(AbilityWord.HELLBENT);
        this.addAbility(hellbentAbility);

    }

    private JaggedPoppet(final JaggedPoppet card) {
        super(card);
    }

    @Override
    public JaggedPoppet copy() {
        return new JaggedPoppet(this);
    }
}

class JaggedPoppetDealsDamageEffect extends OneShotEffect {

    public JaggedPoppetDealsDamageEffect() {
        super(Outcome.Discard);
        //staticText = "it deals that much damage to each creature that player controls";
    }

    public JaggedPoppetDealsDamageEffect(final JaggedPoppetDealsDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //According to the Balefire Dragon code, This statement gets the player that was dealt the combat damage
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            //Call the getValue method of the Effect class to retrieve the amount of damage
            int amount = (Integer) getValue("damage");

            if (amount > 0) {
                //Call the player discard function discarding cards equal to damage
                player.discard(amount, false, false, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public JaggedPoppetDealsDamageEffect copy() {
        return new JaggedPoppetDealsDamageEffect(this);
    }
}

class JaggedPoppetDealtDamageEffect extends OneShotEffect {

    public JaggedPoppetDealtDamageEffect() {
        super(Outcome.Discard);
        staticText = "discard that many cards";
    }

    public JaggedPoppetDealtDamageEffect(final JaggedPoppetDealtDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        //According to the Firedrinker Satyr code, This statement gets the player that controls Jagged Poppet
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            //Call the getValue method of the Effect class to retrieve the amount of damage
            int amount = (Integer) getValue("damage");

            if (amount > 0) {
                //Call the player discard function discarding cards equal to damage
                player.discard(amount, false, false, source, game);
            }
            return true;
        }
        return false;

    }

    @Override
    public JaggedPoppetDealtDamageEffect copy() {
        return new JaggedPoppetDealtDamageEffect(this);
    }
}
