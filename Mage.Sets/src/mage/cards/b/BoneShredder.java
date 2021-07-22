
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
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
 * @author Backfir3
 */
public final class BoneShredder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact, nonblack creature");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public BoneShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EchoAbility("{2}{B}"));

        //When Bone Shredder enters the battlefield, destroy target nonartifact, nonblack creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(false));
        Target target = new TargetCreaturePermanent(filter);
        target.setTargetName("nonartifact, nonblack creature");
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private BoneShredder(final BoneShredder card) {
        super(card);
    }

    @Override
    public BoneShredder copy() {
        return new BoneShredder(this);
    }
}
