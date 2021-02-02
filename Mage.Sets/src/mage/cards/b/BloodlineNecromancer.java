
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class BloodlineNecromancer extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Vampire or Wizard creature card from your graveyard");

    static {
        filter.add(Predicates.or(SubType.VAMPIRE.getPredicate(), SubType.WIZARD.getPredicate()));
    }

    public BloodlineNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.VAMPIRE, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Bloodline Necromancer enters the battlefield, you may return target Vampire or Wizard creature card from your graveyard to the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        Target target = new TargetCardInYourGraveyard(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private BloodlineNecromancer(final BloodlineNecromancer card) {
        super(card);
    }

    @Override
    public BloodlineNecromancer copy() {
        return new BloodlineNecromancer(this);
    }
}
