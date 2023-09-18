package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PublicEnemy extends CardImpl {

    public PublicEnemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // All creatures attack enchanted creature's controller each combat if able.
        this.addAbility(new SimpleStaticAbility(new PublicEnemyEffect()));

        // When enchanted creature dies, draw a card.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new DrawCardSourceControllerEffect(1), "enchanted creature"
        ));
    }

    private PublicEnemy(final PublicEnemy card) {
        super(card);
    }

    @Override
    public PublicEnemy copy() {
        return new PublicEnemy(this);
    }
}

class PublicEnemyEffect extends RequirementEffect {

    PublicEnemyEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "all creatures attack enchanted creature's controller each combat if able";
    }

    private PublicEnemyEffect(final PublicEnemyEffect effect) {
        super(effect);
    }

    @Override
    public PublicEnemyEffect copy() {
        return new PublicEnemyEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
        if (enchantment == null || enchantedCreature == null) {
            return false;
        }
        if (permanent.isControlledBy(enchantedCreature.getControllerId())) {
            return false;
        }
        return true;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getControllerId)
                .orElse(null);
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}
