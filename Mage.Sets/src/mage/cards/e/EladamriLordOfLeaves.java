
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class EladamriLordOfLeaves extends CardImpl {

    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("Elf creatures");
    private static final FilterPermanent filterPermanents = new FilterPermanent("Elves");

    static {
        filterCreatures.add(SubType.ELF.getPredicate());
        filterPermanents.add(SubType.ELF.getPredicate());
    }

    public EladamriLordOfLeaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new ForestwalkAbility(), Duration.WhileOnBattlefield, filterCreatures, true)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filterPermanents, true)));
    }

    private EladamriLordOfLeaves(final EladamriLordOfLeaves card) {
        super(card);
    }

    @Override
    public EladamriLordOfLeaves copy() {
        return new EladamriLordOfLeaves(this);
    }
}
