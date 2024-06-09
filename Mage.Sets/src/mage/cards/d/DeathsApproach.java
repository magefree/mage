
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DeathsApproach extends CardImpl {

    public DeathsApproach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted Creature gets -X/-X, where X is the number of creature cards in its controller's graveyard.
        DynamicValue unboost = new SignInversionDynamicValue(
                new CardsInEnchantedCreaturesControllerGraveyardCount(new FilterCreatureCard("the number of creature cards in its controller's graveyard")));
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(unboost,unboost, Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private DeathsApproach(final DeathsApproach card) {
        super(card);
    }

    @Override
    public DeathsApproach copy() {
        return new DeathsApproach(this);
    }
}

class CardsInEnchantedCreaturesControllerGraveyardCount implements DynamicValue {

    private final FilterCard filter;

    CardsInEnchantedCreaturesControllerGraveyardCount(FilterCard filter) {
        this.filter = filter;
    }

    private CardsInEnchantedCreaturesControllerGraveyardCount(final CardsInEnchantedCreaturesControllerGraveyardCount dynamicValue) {
        this.filter = dynamicValue.filter;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent attachment = game.getPermanent(sourceAbility.getSourceId());
        if (attachment != null) {
            Permanent creature = game.getPermanent(attachment.getAttachedTo());
            if (creature != null) {
                Player player = game.getPlayer(creature.getControllerId());
                if (player != null) {
                    return player.getGraveyard().count(filter, game);
                }
            }

        }
        return 0;
    }

    @Override
    public CardsInEnchantedCreaturesControllerGraveyardCount copy() {
        return new CardsInEnchantedCreaturesControllerGraveyardCount(this);
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return filter.getMessage();
    }
}
