
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LoneFox
 */
public final class StrongholdOverseer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with shadow");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creatures without shadow");

    static {
        filter.add(new AbilityPredicate(ShadowAbility.class));
        filter2.add(Predicates.not(new AbilityPredicate(ShadowAbility.class)));
    }

    public StrongholdOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // {B}{B}: Creatures with shadow get +1/+0 until end of turn and creatures without shadow get -1/-0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 0, Duration.EndOfTurn, filter, false), new ManaCostsImpl<>("{B}{B}"));
        Effect effect = new BoostAllEffect(-1, 0, Duration.EndOfTurn, filter2, false);
        effect.setText("and creatures without shadow get -1/-0 until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private StrongholdOverseer(final StrongholdOverseer card) {
        super(card);
    }

    @Override
    public StrongholdOverseer copy() {
        return new StrongholdOverseer(this);
    }
}
