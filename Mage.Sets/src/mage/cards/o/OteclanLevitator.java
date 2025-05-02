package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OteclanLevitator extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public OteclanLevitator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);
        this.nightCard = true;
        this.color.setWhite(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Oteclan Levitator attacks, target attacking creature without flying gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OteclanLevitator(final OteclanLevitator card) {
        super(card);
    }

    @Override
    public OteclanLevitator copy() {
        return new OteclanLevitator(this);
    }
}
