package mage.cards.i;

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
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class InfiltratorsMagemark extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control that are enchanted");

    static {
        filter.add(EnchantedPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public InfiltratorsMagemark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Creatures you control that are enchanted get +1/+1 and can't be blocked except by creatures with defender.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false));
        Effect effect = new InfiltratorsMagemarkCantBeBlockedAllEffect(filter, Duration.WhileOnBattlefield);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private InfiltratorsMagemark(final InfiltratorsMagemark card) {
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
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getAbilities().contains(DefenderAbility.getInstance());
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
