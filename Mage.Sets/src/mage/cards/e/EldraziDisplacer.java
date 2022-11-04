package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EldraziDisplacer extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("another target creature");

    static {
        FILTER.add(AnotherPredicate.instance);
    }

    public EldraziDisplacer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // {2}{C}: Exile another target creature, then return it to the battlefield tapped under its owner's control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{2}{C}"));
        ability.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, false, "it").concatBy(", then"));
        ability.addTarget(new TargetCreaturePermanent(FILTER));
        this.addAbility(ability);
    }

    private EldraziDisplacer(final EldraziDisplacer card) {
        super(card);
    }

    @Override
    public EldraziDisplacer copy() {
        return new EldraziDisplacer(this);
    }
}
