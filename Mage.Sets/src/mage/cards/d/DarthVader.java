
package mage.cards.d;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public final class DarthVader extends CardImpl {

    public DarthVader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);

        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Darth Vader attacks, creatures defending player controls get -1/-1 until end of turn for each +1/+1 counter on Darth Vader.
        this.addAbility(new AttacksTriggeredAbility(new UnboostCreaturesDefendingPlayerEffect(), false, null, SetTargetPointer.PLAYER));
    }

    private DarthVader(final DarthVader card) {
        super(card);
    }

    @Override
    public DarthVader copy() {
        return new DarthVader(this);
    }
}

class UnboostCreaturesDefendingPlayerEffect extends ContinuousEffectImpl {

    public UnboostCreaturesDefendingPlayerEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        staticText = "creatures defending player controls get -1/-1 until end of turn for each +1/+1 counter on Darth Vader";
    }

    public UnboostCreaturesDefendingPlayerEffect(final UnboostCreaturesDefendingPlayerEffect effect) {
        super(effect);
    }

    @Override
    public UnboostCreaturesDefendingPlayerEffect copy() {
        return new UnboostCreaturesDefendingPlayerEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, getTargetPointer().getFirst(game, source), game)) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                int unboostCount = -1 * new CountersSourceCount(CounterType.P1P1).calculate(game, source, this);
                permanent.addPower(unboostCount);
                permanent.addToughness(unboostCount);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
