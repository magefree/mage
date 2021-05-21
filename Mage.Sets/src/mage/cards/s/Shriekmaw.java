
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Shriekmaw extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact, nonblack creature");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public Shriekmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // When Shriekmaw enters the battlefield, destroy target nonartifact, nonblack creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(),false);
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
        
        // Evoke {1}{B} (You may cast this spell for its evoke cost. If you do, it's sacrificed when it enters the battlefield.)
        this.addAbility(new EvokeAbility("{1}{B}"));
    }

    private Shriekmaw(final Shriekmaw card) {
        super(card);
    }

    @Override
    public Shriekmaw copy() {
        return new Shriekmaw(this);
    }
}
