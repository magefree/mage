
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksFirstTimeTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class GodoBanditWarlord extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment card");
    private static final FilterControlledCreaturePermanent filter2 = new FilterControlledCreaturePermanent(SubType.SAMURAI);

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public GodoBanditWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARBARIAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Godo, Bandit Warlord enters the battlefield, you may search your library for an Equipment card and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), false), true));

        // Whenever Godo attacks for the first time each turn, untap it and all Samurai you control. After this phase, there is an additional combat phase.
        Ability ability = new AttacksFirstTimeTriggeredAbility(new UntapSourceEffect().setText("untap it"), false);
        ability.addEffect(new UntapAllControllerEffect(filter2, "and all Samurai you control"));
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    private GodoBanditWarlord(final GodoBanditWarlord card) {
        super(card);
    }

    @Override
    public GodoBanditWarlord copy() {
        return new GodoBanditWarlord(this);
    }
}
