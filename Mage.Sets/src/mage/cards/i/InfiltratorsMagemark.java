
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class InfiltratorsMagemark extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control that are enchanted");
    static {
        filter.add(new EnchantedPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public InfiltratorsMagemark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Creatures you control that are enchanted get +1/+1 and can't be blocked except by creatures with defender.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,1, Duration.WhileOnBattlefield, filter, false));
        Effect effect = new InfiltratorsMagemarkCantBeBlockedAllEffect(filter, Duration.WhileOnBattlefield);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public InfiltratorsMagemark(final InfiltratorsMagemark card) {
        super(card);
    }

    @Override
    public InfiltratorsMagemark copy() {
        return new InfiltratorsMagemark(this);
    }
}

class InfiltratorsMagemarkCantBeBlockedAllEffect extends RestrictionEffect {

    private final FilterPermanent filter;

    public InfiltratorsMagemarkCantBeBlockedAllEffect(FilterPermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;
        this.staticText = "and can't be blocked except by creatures with defender";
    }

    public InfiltratorsMagemarkCantBeBlockedAllEffect(InfiltratorsMagemarkCantBeBlockedAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public InfiltratorsMagemarkCantBeBlockedAllEffect copy() {
        return new InfiltratorsMagemarkCantBeBlockedAllEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return blocker.getAbilities().contains(DefenderAbility.getInstance());
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getSourceId(), source.getControllerId(), game);
    }
}
