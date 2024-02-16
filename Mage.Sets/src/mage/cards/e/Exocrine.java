package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Exocrine extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public Exocrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{2}{R}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Bio-plasmic Barrage -- When Exocrine enters the battlefield, it deals X damage to each player and each other creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(
                Outcome.Damage, ManacostVariableValue.ETB, TargetController.ANY, "it"
        ));
        ability.addEffect(new DamageAllEffect(ManacostVariableValue.ETB, filter).setText("and each other creature"));
        this.addAbility(ability.withFlavorWord("Bio-plasmic Barrage"));
    }

    private Exocrine(final Exocrine card) {
        super(card);
    }

    @Override
    public Exocrine copy() {
        return new Exocrine(this);
    }
}
