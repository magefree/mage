
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author emerald000
 */
public final class ScuttlingDoomEngine extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public ScuttlingDoomEngine(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Scuttling Doom Engine can't be blocked by creatures with power 2 or less.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)));
        // When Scuttling Doom Engine dies, it deals 6 damage to target opponnent
        Ability ability = new DiesTriggeredAbility(new DamageTargetEffect(6, "it"), false);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    public ScuttlingDoomEngine(final ScuttlingDoomEngine card) {
        super(card);
    }

    @Override
    public ScuttlingDoomEngine copy() {
        return new ScuttlingDoomEngine(this);
    }
}
