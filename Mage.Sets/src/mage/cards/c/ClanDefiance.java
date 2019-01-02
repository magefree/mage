
package mage.cards.c;

import java.util.UUID;
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

/**
 *
 * @author jeffwadsworth
 */
public final class ClanDefiance extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    static final private FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public ClanDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{G}");

        // Choose one or more - 
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);
        // Clan Defiance deals X damage to target creature with flying;
        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        // Clan Defiance deals X damage to target creature without flying;
        Mode mode1 = new Mode();
        mode1.addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        mode1.addTarget(new TargetCreaturePermanent(filter2));
        this.getSpellAbility().addMode(mode1);
        // and/or Clan Defiance deals X damage to target player.
        Mode mode2 = new Mode();
        mode2.addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        mode2.addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addMode(mode2);

    }

    public ClanDefiance(final ClanDefiance card) {
        super(card);
    }

    @Override
    public ClanDefiance copy() {
        return new ClanDefiance(this);
    }

}
