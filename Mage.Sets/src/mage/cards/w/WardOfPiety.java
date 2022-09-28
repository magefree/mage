
package mage.cards.w;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class WardOfPiety extends CardImpl {

    public WardOfPiety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PreventDamage));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // {1}{W}: The next 1 damage that would be dealt to enchanted creature this turn is dealt to any target instead.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WardOfPietyPreventDamageTargetEffect(), new ManaCostsImpl<>("{1}{W}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private WardOfPiety(final WardOfPiety card) {
        super(card);
    }

    @Override
    public WardOfPiety copy() {
        return new WardOfPiety(this);
    }
}

class WardOfPietyPreventDamageTargetEffect extends RedirectionEffect {

    protected MageObjectReference redirectToObject;

    public WardOfPietyPreventDamageTargetEffect() {
        super(Duration.EndOfTurn, 1, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next 1 damage that would be dealt to enchanted creature this turn is dealt to any target instead";
    }

    public WardOfPietyPreventDamageTargetEffect(final WardOfPietyPreventDamageTargetEffect effect) {
        super(effect);
        this.redirectToObject = effect.redirectToObject;
    }

    @Override
    public WardOfPietyPreventDamageTargetEffect copy() {
        return new WardOfPietyPreventDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        redirectToObject = new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
            if (redirectToObject.equals(new MageObjectReference(source.getTargets().get(0).getFirstTarget(), game))) {
                redirectTarget = source.getTargets().get(0);
                return true;
            }
        }
        return false;
    }

}
