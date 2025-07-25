package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ClanDefiance extends CardImpl {

    static final private FilterCreaturePermanent filterWithoutFlying = new FilterCreaturePermanent("creature without flying");

    static {
        filterWithoutFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public ClanDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{G}");

        // Choose one or more - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);
        // Clan Defiance deals X damage to target creature with flying;
        this.getSpellAbility().addEffect(new DamageTargetEffect(GetXValue.instance));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING).withChooseHint("deals X damage, with flying"));
        // Clan Defiance deals X damage to target creature without flying;
        Mode mode1 = new Mode(new DamageTargetEffect(GetXValue.instance));
        mode1.addTarget(new TargetPermanent(filterWithoutFlying).withChooseHint("deals X damage, without flying"));
        this.getSpellAbility().addMode(mode1);
        // and/or Clan Defiance deals X damage to target player.
        Mode mode2 = new Mode(new DamageTargetEffect(GetXValue.instance));
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
