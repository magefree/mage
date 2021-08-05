package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GladewalkerRitualist extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another creature named Gladewalker Ritualist");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new NamePredicate("Gladewalker Ritualist"));
    }

    public GladewalkerRitualist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // Whenever another creature named Gladewalker Ritualist enters the battlefield under your control, draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter
        ));
    }

    private GladewalkerRitualist(final GladewalkerRitualist card) {
        super(card);
    }

    @Override
    public GladewalkerRitualist copy() {
        return new GladewalkerRitualist(this);
    }
}
