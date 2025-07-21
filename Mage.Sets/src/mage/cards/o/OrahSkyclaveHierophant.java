package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrahSkyclaveHierophant extends CardImpl {

    private static final FilterPermanent filterTrigger = new FilterControlledPermanent("Cleric you control");
    private static final FilterCard filterTarget = new FilterCard("Cleric card with lesser mana value");
    static {
        filterTrigger.add(SubType.CLERIC.getPredicate());
        filterTarget.add(SubType.CLERIC.getPredicate());
        filterTarget.add(OrahSkyclaveHierophantPredicate.instance);
    }

    public OrahSkyclaveHierophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Orah, Skyclave Hierophant or another Cleric you control dies, return target Cleric card with lesser converted mana cost from your graveyard to the battlefield.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), false, filterTrigger);
        ability.addTarget(new TargetCardInYourGraveyard(filterTarget));
        this.addAbility(ability);
    }

    private OrahSkyclaveHierophant(final OrahSkyclaveHierophant card) {
        super(card);
    }

    @Override
    public OrahSkyclaveHierophant copy() {
        return new OrahSkyclaveHierophant(this);
    }
}

enum OrahSkyclaveHierophantPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return CardUtil.getEffectValueFromAbility(
                        input.getSource(), "creatureDied", Permanent.class
                )
                .filter(permanent -> input.getObject().getManaValue() < permanent.getManaValue())
                .isPresent();
    }
}
