package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class DelneyStreetwiseLookout extends CardImpl {

    private static final FilterCreaturePermanent filterSmall = new FilterCreaturePermanent("creatures you control with power 2 or less");
    private static final FilterCreaturePermanent filterBig = new FilterCreaturePermanent("creatures with power 3 or greater");
    static {
        filterSmall.add(new PowerPredicate(ComparisonType.OR_LESS, 2));
        filterSmall.add(TargetController.YOU.getControllerPredicate());
        filterBig.add(new PowerPredicate(ComparisonType.OR_GREATER, 3));
    }

    public DelneyStreetwiseLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creatures you control with power 2 or less can't be blocked by creatures with power 3 or greater.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAllEffect(
                filterSmall, filterBig, Duration.WhileOnBattlefield
        )));

        // If an ability of a creature you control with power 2 or less triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filterSmall)));
    }

    private DelneyStreetwiseLookout(final DelneyStreetwiseLookout card) {
        super(card);
    }

    @Override
    public DelneyStreetwiseLookout copy() {
        return new DelneyStreetwiseLookout(this);
    }
}
