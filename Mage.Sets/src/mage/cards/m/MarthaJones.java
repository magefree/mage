package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MarthaJones extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.CLUE, "a Clue");
    private static final FilterCreaturePermanent filterOther = new FilterCreaturePermanent("other target creature");

    static {
        filterOther.add(AnotherPredicate.instance);
    }

    public MarthaJones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Woman Who Walked the Earth -- When Martha Jones enters the battlefield, investigate.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new InvestigateEffect(), false
        ).withFlavorWord("Woman Who Walked the Earth"));

        // Whenever you sacrifice a Clue, Martha Jones and up to one other target creature can't be blocked this turn.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("{this}"), filter
        );
        ability.addEffect(new CantBeBlockedTargetEffect()
                .setText("and up to one other target creature can't be blocked this turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1, filterOther, false));
        this.addAbility(ability);

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private MarthaJones(final MarthaJones card) {
        super(card);
    }

    @Override
    public MarthaJones copy() {
        return new MarthaJones(this);
    }
}
