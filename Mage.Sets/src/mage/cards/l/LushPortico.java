package mage.cards.l;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LushPortico extends CardImpl {

    public LushPortico(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.PLAINS);

        // ({T}: Add {G} or {W}.)
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Lush Portico enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Lush Portico enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private LushPortico(final LushPortico card) {
        super(card);
    }

    @Override
    public LushPortico copy() {
        return new LushPortico(this);
    }
}
