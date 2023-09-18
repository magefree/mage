package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class TeysaOrzhovScion extends CardImpl {

    private static final FilterControlledCreaturePermanent filterWhite = new FilterControlledCreaturePermanent("white creatures");
    private static final FilterCreaturePermanent filterBlack = new FilterCreaturePermanent("another black creature you control");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterBlack.add(AnotherPredicate.instance);
        filterBlack.add(TargetController.YOU.getControllerPredicate());
    }

    public TeysaOrzhovScion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Sacrifice three white creatures: Exile target creature.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(3, 3, filterWhite, true)));
        ability.addTarget(new TargetCreaturePermanent().withChooseHint("to exile"));
        this.addAbility(ability);

        // Whenever another black creature you control dies, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken()), false, filterBlack));
    }

    private TeysaOrzhovScion(final TeysaOrzhovScion card) {
        super(card);
    }

    @Override
    public TeysaOrzhovScion copy() {
        return new TeysaOrzhovScion(this);
    }
}
