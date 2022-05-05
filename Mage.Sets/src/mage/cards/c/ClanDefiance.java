package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ClanDefiance extends CardImpl {

    static final private FilterCreaturePermanent filterFlying = new FilterCreaturePermanent("creature with flying");
    static final private FilterCreaturePermanent filterWithoutFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterFlying.add(new AbilityPredicate(FlyingAbility.class));
        filterWithoutFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public ClanDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{G}");

        // Choose one or more - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);
        // Clan Defiance deals X damage to target creature with flying;
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterFlying).withChooseHint("deals X damage, with flying"));
        // Clan Defiance deals X damage to target creature without flying;
        Mode mode1 = new Mode(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        mode1.addTarget(new TargetCreaturePermanent(filterWithoutFlying).withChooseHint("deals X damage, without flying"));
        this.getSpellAbility().addMode(mode1);
        // and/or Clan Defiance deals X damage to target player.
        Mode mode2 = new Mode(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        mode2.addTarget(new TargetPlayerOrPlaneswalker().withChooseHint("deals X damage"));
        this.getSpellAbility().addMode(mode2);

    }

    private ClanDefiance(final ClanDefiance card) {
        super(card);
    }

    @Override
    public ClanDefiance copy() {
        return new ClanDefiance(this);
    }

}
