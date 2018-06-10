
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author Loki
 */
public final class ImperiousPerfect extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elf creatures");

    static {
        filter.add(new SubtypePredicate(SubType.ELF));
    }

    public ImperiousPerfect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ElfToken(), 1), new ColoredManaCost(ColoredManaSymbol.G));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public ImperiousPerfect(final ImperiousPerfect card) {
        super(card);
    }

    @Override
    public ImperiousPerfect copy() {
        return new ImperiousPerfect(this);
    }
}
