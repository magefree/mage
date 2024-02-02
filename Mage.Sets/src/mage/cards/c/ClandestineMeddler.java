package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SuspectTargetEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.SuspectedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClandestineMeddler extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();
    private static final FilterPermanent filterOther = new FilterControlledCreaturePermanent("other target creature you control");

    static {
        filter.add(SuspectedPredicate.instance);
        filterOther.add(AnotherPredicate.instance);
    }

    public ClandestineMeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Clandestine Meddler enters the battlefield, suspect up to one other target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SuspectTargetEffect());
        ability.addTarget(new TargetPermanent(
                0, 1, filterOther
        ));
        this.addAbility(ability);

        // Whenever one or more suspected creatures you control attack, surveil 1.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
                new SurveilEffect(1), 1, filter
        ).setTriggerPhrase("Whenever one or more suspected creatures you control attack, "));
    }

    private ClandestineMeddler(final ClandestineMeddler card) {
        super(card);
    }

    @Override
    public ClandestineMeddler copy() {
        return new ClandestineMeddler(this);
    }
}
