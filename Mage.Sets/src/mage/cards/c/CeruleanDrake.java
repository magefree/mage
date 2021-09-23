package mage.cards.c;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CeruleanDrake extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets you");

    static {
        filter.add(CeruleanDrakePredicate.instance);
    }

    public CeruleanDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));

        // Sacrifice Cerulean Drake: Counter target spell that targets you.
        Ability ability = new SimpleActivatedAbility(new CounterTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private CeruleanDrake(final CeruleanDrake card) {
        super(card);
    }

    @Override
    public CeruleanDrake copy() {
        return new CeruleanDrake(this);
    }
}

enum CeruleanDrakePredicate implements ObjectSourcePlayerPredicate<StackObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        if (input.getPlayerId() == null) {
            return false;
        }
        return input
                .getObject()
                .getStackAbility()
                .getTargets()
                .stream()
                .anyMatch(
                        target -> target
                                .getTargets()
                                .stream()
                                .anyMatch(uuid -> uuid != null && uuid.equals(input.getPlayerId()))
                );
    }
}
