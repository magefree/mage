package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class DoctorJaneFoster extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public DoctorJaneFoster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Doctor Jane Foster enters, return target creature card with mana value 3 or less from your graveyard to your hand. If you gained life this turn, return that card to the battlefield instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(
            new ConditionalOneShotEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new ReturnFromGraveyardToHandTargetEffect(),
                YouGainedLifeCondition.getZero(),
                "return target creature card with mana value 3 or less from your graveyard to your hand."
                + "If you gained life this turn, return that card to the battlefield instead"
            )
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private DoctorJaneFoster(final DoctorJaneFoster card) {
        super(card);
    }

    @Override
    public DoctorJaneFoster copy() {
        return new DoctorJaneFoster(this);
    }
}
