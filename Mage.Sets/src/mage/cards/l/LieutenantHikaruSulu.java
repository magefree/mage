package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author muz
 */
public final class LieutenantHikaruSulu extends CardImpl {

    public LieutenantHikaruSulu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Lieutenant Sulu becomes tapped, he explores.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new ExploreSourceEffect()));

        // {2}{U}: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LieutenantHikaruSulu(final LieutenantHikaruSulu card) {
        super(card);
    }

    @Override
    public LieutenantHikaruSulu copy() {
        return new LieutenantHikaruSulu(this);
    }
}
