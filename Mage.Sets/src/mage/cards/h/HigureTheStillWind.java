
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class HigureTheStillWind extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ninja card");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("Ninja creature");

    static {
        filter.add(SubType.NINJA.getPredicate());
        filterCreature.add((SubType.NINJA.getPredicate()));
    }

    public HigureTheStillWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        addSuperType(SuperType.LEGENDARY);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ninjutsu {2}{U}{U} ({2}{U}{U}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility("{2}{U}{U}"));

        // Whenever Higure, the Still Wind deals combat damage to a player, you may search your library for a Ninja card, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, false), true));

        // {2}: Target Ninja creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(filterCreature));
        this.addAbility(ability);

    }

    private HigureTheStillWind(final HigureTheStillWind card) {
        super(card);
    }

    @Override
    public HigureTheStillWind copy() {
        return new HigureTheStillWind(this);
    }
}
