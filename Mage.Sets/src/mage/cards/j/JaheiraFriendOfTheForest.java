package mage.cards.j;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JaheiraFriendOfTheForest extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public JaheiraFriendOfTheForest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Tokens you control have "{T}: Add {G}."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new GreenManaAbility(), Duration.WhileOnBattlefield, filter
        )));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private JaheiraFriendOfTheForest(final JaheiraFriendOfTheForest card) {
        super(card);
    }

    @Override
    public JaheiraFriendOfTheForest copy() {
        return new JaheiraFriendOfTheForest(this);
    }
}
