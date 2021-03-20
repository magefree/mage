package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.SourceOnBattlefieldControlUnchangedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.watchers.common.LostControlWatcher;

/**
 * @author Loki
 */
public final class AegisAngel extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AegisAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Aegis Angel enters the battlefield, another target permanent is indestructible for as long as you control Aegis Angel.
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.Custom),
                new SourceOnBattlefieldControlUnchangedCondition(),
                "another target permanent is indestructible for as long as you control Aegis Angel");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetPermanent(filter));
        ability.addWatcher(new LostControlWatcher());
        this.addAbility(ability);
    }

    private AegisAngel(final AegisAngel card) {
        super(card);
    }

    @Override
    public AegisAngel copy() {
        return new AegisAngel(this);
    }

}
